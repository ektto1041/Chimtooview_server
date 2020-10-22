package com.yeon.chimtooview.Service;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPQLQuery;
import com.yeon.chimtooview.Entity.Playlist;
import com.yeon.chimtooview.Entity.QVideo;
import com.yeon.chimtooview.Entity.Video;
import com.yeon.chimtooview.Repository.VideoRepository;
import com.yeon.chimtooview.Util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VideoService extends QuerydslRepositorySupport {
    public VideoService() { super(Video.class); }

    @Autowired
    private VideoRepository videoRepository;

    public List<Video> getVideoAll() {
        return videoRepository.findAll();
    }

    // 요약 화면의 TopFive 를 구성하기 위해 DB에서 Sort하여 Data를 가져오는 Service
    public List<List<Video>> getVideoAllForTopFive() {
        // TODO 이거 개선하자 아니면 아예 요약 페이지를 바꿔버리자
//        DATE: 0,
//        VIEW_COUNT: 1,
//        LIKE_COUNT: 2,
//        DISLIKE_COUNT: 3,
//        LENGTH: 4, --- 제외
//        LIKE_RATE: 5,
//        LIKE_GAP: 6,
//        VIEW_LIKE_RATE: 7,
//        VIEW_LIKE_GAP: 8,
//        LIKE_LENGTH_RATE: 9, --- 제외

        QVideo qVideo = QVideo.video;

        List<List<Video>> topFiveVideoList = new ArrayList<>();

        topFiveVideoList.add(from(qVideo).orderBy(qVideo.publishedAt.desc()).limit(5).fetch());
        topFiveVideoList.add(from(qVideo).orderBy(qVideo.viewCount.desc()).limit(5).fetch());
        topFiveVideoList.add(from(qVideo).orderBy(qVideo.likeCount.desc()).limit(5).fetch());
        topFiveVideoList.add(from(qVideo).orderBy(qVideo.dislikeCount.desc()).limit(5).fetch());
        topFiveVideoList.add(from(qVideo).orderBy(qVideo.likeDislikeRate.desc()).limit(5).fetch());
        topFiveVideoList.add(from(qVideo).orderBy(qVideo.likeDislikeGap.desc()).limit(5).fetch());
        topFiveVideoList.add(from(qVideo).orderBy(qVideo.viewLikeRate.desc()).limit(5).fetch());
        topFiveVideoList.add(from(qVideo).orderBy(qVideo.viewLikeGap.desc()).limit(5).fetch());

        return topFiveVideoList;
    }

    public List<Video> getVideoAllByPlaylist(Playlist playlist) {
        return videoRepository.findAllByPlaylist(playlist);
    }

    public long getVideoAllCountByFilter(String searchWord, String searchWordPlaylist) {
        QVideo qVideo = QVideo.video;

        JPQLQuery<Video> query = from(qVideo);

        if(!searchWord.equals("__BLANK")) {
            query = query.where(qVideo.title.contains(searchWord));
        }

        if(!searchWordPlaylist.equals("__BLANK")) {
            query = query.where(qVideo.playlist.title.contains(searchWordPlaylist));
        }

        long count = query.fetchCount();

        return count;
    }

    public QueryResults<Video> getVideoAllOrderByPaging(int category, int order, String searchWord, String searchWordPlaylist, int pageCurrent) {
        QVideo qVideo = QVideo.video;
        OrderSpecifier orderSpecifier = null;

        if(category == Constants.DATE) {
            orderSpecifier = (order == 0) ? qVideo.publishedAt.asc() : qVideo.publishedAt.desc();
        } else if(category > Constants.DATE) {
            NumberPath numberPath = null;

            switch (category) {
                case Constants.VIEW_COUNT:
                    numberPath = qVideo.viewCount;
                    break;
                case Constants.LIKE_COUNT:
                    numberPath = qVideo.likeCount;
                    break;
                case Constants.DISLIKE_COUNT:
                    numberPath = qVideo.dislikeCount;
                    break;
                case Constants.DURATION:
                    numberPath = qVideo.duration;
                    break;
                case Constants.LIKE_DISLIKE_RATE:
                    numberPath = qVideo.likeDislikeRate;
                    break;
                case Constants.LIKE_DISLIKE_GAP:
                    numberPath = qVideo.likeDislikeGap;
                    break;
                case Constants.VIEW_LIKE_RATE:
                    numberPath = qVideo.viewLikeRate;
                    break;
                case Constants.VIEW_LIKE_GAP:
                    numberPath = qVideo.viewLikeGap;
                    break;
                case Constants.LIKE_DURATION_RATE:
                    numberPath = qVideo.likeDurationRate;
                    break;
            }

            orderSpecifier = (order == 0) ? numberPath.asc() : numberPath.desc();
        }

        JPQLQuery<Video> query = from(qVideo).orderBy(orderSpecifier).offset(50 * (pageCurrent-1)).limit(50);

        // 검색어가 __BLANK 가 아닐 경우의 처리 (검색어가 제대로 입력되었을 때)
        if(!searchWord.equals("__BLANK")) {
            query = query.where(qVideo.title.contains(searchWord));
        }

        if(!searchWordPlaylist.equals("__BLANK")) {
            query = query.where(qVideo.playlist.title.contains(searchWordPlaylist));
        }

        QueryResults<Video> queryResults = query.fetchResults();

        return queryResults;
    }

    public List<Video> getVideoOrderByViewCountLimit10() {
        QVideo qVideo = QVideo.video;

        List<Video> videoList = from(qVideo).orderBy(qVideo.viewCount.desc()).limit(10).fetch();

        return videoList;
    }

    public List<Video> getVideoOrderByLikeCountLimit10() {
        QVideo qVideo = QVideo.video;

        List<Video> videoList = from(qVideo).orderBy(qVideo.likeCount.desc()).limit(10).fetch();

        return videoList;
    }

    public List<Video> getVideoOrderByDislikeCountLimit10() {
        QVideo qVideo = QVideo.video;

        List<Video> videoList = from(qVideo).orderBy(qVideo.dislikeCount.desc()).limit(10).fetch();

        return videoList;
    }

    public Video postVideo(Video video) {
        Video videoFromDb = videoRepository.findById(video.getId()).orElse(null);

        Video result = null;

        if(videoFromDb == null) {
            result = videoRepository.save(video);
        } else {
            result = videoFromDb;
        }

        return result;
    }
}
